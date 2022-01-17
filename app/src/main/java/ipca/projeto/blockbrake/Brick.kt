package ipca.projeto.blockbrake

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import java.util.*

class Brick {

    var x:Float
    var y:Float

    var cor:Int
    var detectColision: Rect
    var width : Float
    var height:Float


    constructor(context: Context, screenWidht:Int, screenHeight: Int,x_:Int,y_:Int){

        width=screenWidht/6f
        height=screenHeight/50f
        var RandomColor= Random()
        x=x_*width
        y=y_*height
        cor=RandomColor.nextInt(4)

        detectColision= Rect(x.toInt(),y.toInt(),width.toInt(),height.toInt())

        detectColision.left=x.toInt()
        detectColision.top=y.toInt()
        detectColision.right=x.toInt()+width.toInt()
        detectColision.bottom=y.toInt()+height.toInt()

    }

    fun update(){
        y+=height

        detectColision.left=x.toInt()
        detectColision.top=y.toInt()
        detectColision.right=x.toInt()+width.toInt()
        detectColision.bottom=y.toInt()+height.toInt()
    }


}
