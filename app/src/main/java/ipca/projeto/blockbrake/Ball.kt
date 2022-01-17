package ipca.projeto.blockbrake

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import kotlin.random.Random

class Ball {

    var x:Float
    var y:Float
    var speed:Int=0

    var vx:Float
    var vy:Float

    var maxY:Float
    var minY:Float
    var maxX :Float
    var minX :Float

    var width : Float
    var height:Float

    var detectColision: Rect
    var whatCollided:Int


    constructor(context: Context, screenWidht:Int, screenHeight: Int){





        height=screenHeight/50f
        width= height

        x=screenWidht/2f
        y=screenHeight/3*2f

        y-=height

        maxX=screenWidht.toFloat()-width
        minX=0F
        maxY=screenHeight.toFloat()-height
        minY=0f

        var randomVector= java.util.Random()

        vy=-screenHeight/1520f

        vx=screenWidht/720f

        var floatadd=randomVector.nextFloat()*(vy/1.5f)+(vy/3f)

        vy-=floatadd

        vx-=floatadd

        speed=10

        detectColision= Rect(x.toInt(),y.toInt(),width.toInt(),height.toInt())

        whatCollided=-1

    }


    fun update(){



        if(y<minY||y>maxY)
            vy=-vy
        if(x<minX||x>maxX)
            vx=-vx

        y-=vy*speed

        x+=vx*speed


        detectColision.left=x.toInt()
        detectColision.top=y.toInt()
        detectColision.right=x.toInt()+width.toInt()
        detectColision.bottom=y.toInt()+height.toInt()


    }
}