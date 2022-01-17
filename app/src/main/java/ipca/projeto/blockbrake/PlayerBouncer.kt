package ipca.projeto.blockbrake

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class PlayerBouncer {


    var x:Float
    var y:Float
    var width : Float
    var height:Float

    var detectColision:Rect


    constructor(context: Context, screenWidht:Int, screenHeight: Int){
        x=screenWidht/2f
        y=screenHeight/3*2f
        width=screenWidht/4f
        height=screenHeight/40f

        detectColision= Rect(x.toInt(),y.toInt(),width.toInt(),height.toInt())

    }

    fun update(){

        detectColision.left=x.toInt()
        detectColision.top=y.toInt()
        detectColision.right=x.toInt()+width.toInt()
        detectColision.bottom=y.toInt()+height.toInt()
    }
}