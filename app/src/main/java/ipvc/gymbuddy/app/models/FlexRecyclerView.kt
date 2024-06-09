package ipvc.gymbuddy.app.models

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class FlexRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val heightSpecCustom = MeasureSpec.makeMeasureSpec(
            Integer.MAX_VALUE shr 2, MeasureSpec.AT_MOST
        )
        super.onMeasure(widthSpec, heightSpecCustom)

        val params = layoutParams
        params.height = measuredHeight
    }
}