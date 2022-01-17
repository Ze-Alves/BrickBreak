package ipca.projeto.blockbrake

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class GameView : SurfaceView, Runnable {

        var playing = false
        lateinit var gameThread : Thread

        val cores= arrayOf(Color.BLUE,Color.RED,Color.YELLOW,Color.GREEN)

        lateinit var player:PlayerBouncer

        var timer:Int=0

        var _screenHeight:Int = 0
    var _screenWidht:Int = 0

        lateinit var ball:Ball

        var bricks = arrayListOf<Brick>()

         var OnGameOver : (()->Unit)?=null

    var pontosb:String=" "

    val uid=FirebaseAuth.getInstance().uid.toString()
    var uid2:String?=null
         val storage = Firebase.firestore
     var pontos:Int=0

    lateinit var user:User
    lateinit var user2:User

        lateinit var surfaceHolder: SurfaceHolder
        var canvas: Canvas?=null
        lateinit var paint: Paint

        var name:String=""

        var maxpoints:Int=0

        var name2:String=""

        var maxpoints2:Int=0

        var pontos2:Int=0

        constructor(context: Context?, screenWidht:Int, screenHeight:Int,uid2:String?) : super(context){
            init(context,screenWidht,screenHeight,uid2)
        }
        constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
            init(context)
        }
        constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
        ){
            init(context)
        }

        fun init(context: Context?, screenWidht:Int=0, screenHeight:Int=0, uid2p: String? =""){

            player= PlayerBouncer(context!!,screenWidht,screenHeight)

            ball=Ball(context!!,screenWidht,screenHeight)

            surfaceHolder=holder
            paint= Paint()

            _screenHeight=screenHeight
            _screenWidht=screenWidht

            storage.collection("users").document(uid)
                .get().addOnSuccessListener {
                    user= User.fromHashMap(it.data as Map<String, Any>)
                    name=user.name.toString()
                    maxpoints= user.maxPoints?.toInt()!!

                }.addOnFailureListener{
                    Log.d(TAG, "Failed to Load Current Data\n\n\n")
                }

            uid2=uid2p
            if(uid2!=null)
                storage.collection("users").document(uid2!!)
                    .get().addOnSuccessListener {
                        user2= it.data?.let { it1 -> User.fromHashMap(it1) }!!

                        name2=user2.name.toString()
                        maxpoints2= user2.maxPoints?.toInt()!!
                        pontos2= user2.lastPoints?.toInt()!!
                    }

            for(i in 0..3){
                for(j in 0..5){
                    bricks.add(Brick(context,screenWidht,screenHeight,j,
                        i))
                }
            }
        }

        override fun run() {
            while(playing){
                update()
                draw()
                control()
            }
        }

        fun resume(){
            playing = true
            gameThread = Thread(this)

            gameThread.start()
        }

        fun pause() {
            playing = false
            gameThread.join()
        }

        fun update(){

            player.update()
            ball.update()

            if(ball.detectColision.intersect(player.detectColision)) {
                if(ball.detectColision.top==player.detectColision.top||ball.detectColision.bottom==player.detectColision.bottom){
                    ball.vy*=-1
                }
                 if(ball.detectColision.left==player.detectColision.left||ball.detectColision.right==player.detectColision.right)
                 {ball.vx*=-1
                 }
            }

            var index=0
            for(b in bricks){
            if(ball.detectColision.intersect(b.detectColision)) {

                if(ball.detectColision.top==b.detectColision.top||ball.detectColision.bottom==b.detectColision.bottom)
                ball.vy*=-1
                else if(ball.detectColision.left==b.detectColision.left||ball.detectColision.right==b.detectColision.right)
                    ball.vx*=-1
                b.y=-100f
                b.update()
                pontos++

                if(pontos>maxpoints){
                    maxpoints=pontos
                    storage.collection("users").document(uid)
                        .update("MaxPoints", pontos)
                }

                storage.collection("users").document(uid)
                    .update("LastPoints", pontos)

                ball.whatCollided=index

                    b.x=-1000f
            }
            index++
            }

            if(ball.whatCollided!=-1){
                bricks.removeAt(ball.whatCollided)
                if(pontos%6==0&&pontos>0){
                    for(b in bricks)
                        b.update()

                    for(i in 0..5)
                        bricks.add(Brick(context,_screenWidht,_screenHeight,i,
                            0))
                }
                       ball.whatCollided=-1
            }


           if(ball.y>_screenHeight-ball.height||ball.y<0)
                OnGameOver?.invoke()
        }

        fun draw(){

            if(surfaceHolder.surface.isValid){
                canvas=surfaceHolder.lockCanvas()
                canvas?.drawColor(Color.BLACK)
                paint.color=Color.YELLOW

                for(b in bricks) {

                    paint.style = Paint.Style.FILL
                    paint.color=cores[b.cor]
                    canvas?.drawRect(b.detectColision,paint)

                    paint.color=Color.BLACK
                    paint.style = Paint.Style.STROKE
                    canvas?.drawRect(b.detectColision,paint)
                }

                paint.style = Paint.Style.FILL
                paint.color=Color.WHITE
                canvas?.drawRect(player.detectColision,paint)

                canvas?.drawRect(ball.detectColision,paint)

                paint.color=Color.BLACK
                paint.style = Paint.Style.STROKE
                canvas?.drawRect(player.detectColision,paint)

                paint.color=Color.WHITE
                paint.textSize=48f
                canvas?.drawText(name,0f,_screenHeight/2f,paint)
                canvas?.drawText("Pontos: "+pontos.toString(),0f,_screenHeight/2f+60,paint)
                canvas?.drawText("MaxPontos: "+maxpoints.toString(),0f,_screenHeight/2f+120,paint)

                if(uid2!= null){
                    canvas?.drawText(name2,0f,_screenHeight/2f+240,paint)
                    canvas?.drawText("Pontos: "+pontos2.toString(),0f,_screenHeight/2f+300,paint)
                    canvas?.drawText("MaxPontos: "+maxpoints2.toString(),0f,_screenHeight/2f+360,paint)
                }

                canvas?.drawRect(ball.detectColision,paint)
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }

        fun control(){
            Thread.sleep(17)
            timer++
            if(timer>59&&uid2!=null){
                timer=0
                storage.collection("users").document(uid2!!)
                    .get().addOnSuccessListener {
                        user2= it.data?.let { it1 -> User.fromHashMap(it1) }!!
                        pontos2= user2.lastPoints?.toInt()!!
                        maxpoints2= user2.maxPoints?.toInt()!!
                    }
            }
        }

        override fun onTouchEvent(event: MotionEvent?): Boolean {

            var x: Float =event!!.getX()

            player.x=x-player.width/2

            return true
        }
    }
