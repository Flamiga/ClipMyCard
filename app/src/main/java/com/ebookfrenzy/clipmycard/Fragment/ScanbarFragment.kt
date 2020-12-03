package com.ebookfrenzy.clipmycard.Fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.DialogFragment
import com.ebookfrenzy.clipmycard.R
import com.ebookfrenzy.clipmycard.models.Card
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer
import kotlinx.android.synthetic.main.scanbar_layout.*

open class ScanbarFragment(card: Card, context: Context) : DialogFragment() {

    lateinit var image: ImageView
    lateinit var text: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.scanbar_layout, container)
        image = view.findViewById(R.id.image_barcode)
        text = view.findViewById(R.id.text_barcode_number)
        dialog?.setTitle(R.string.scanbar_title)
        displayBitmap("12351652545")
        return view
    }
    /***Code from https://www.brightec.co.uk/blog/howto-creating-barcode-kotlin-android ***/

    private fun createBarcodeBitmap(
        barcodeValue: String,
        @ColorInt barcodeColor: Int,
        @ColorInt backgroundColor: Int,
        widthPixels: Int,
        heightPixels: Int
    ): Bitmap {
        val bitMatrix = Code128Writer().encode(
            barcodeValue,
            BarcodeFormat.CODE_128,
            widthPixels,
            heightPixels
        )

        val pixels = IntArray(bitMatrix.width * bitMatrix.height)
        for (y in 0 until bitMatrix.height) {
            val offset = y * bitMatrix.width
            for (x in 0 until bitMatrix.width) {
                pixels[offset + x] =
                    if (bitMatrix.get(x, y)) barcodeColor else backgroundColor
            }
        }

        val bitmap = Bitmap.createBitmap(
            bitMatrix.width,
            bitMatrix.height,
            Bitmap.Config.ARGB_8888
        )
        bitmap.setPixels(
            pixels,
            0,
            bitMatrix.width,
            0,
            0,
            bitMatrix.width,
            bitMatrix.height
        )
        return bitmap
    }

    private fun displayBitmap(value: String) {
        val widthPixels = resources.getDimensionPixelSize(R.dimen.width_barcode)
        val heightPixels = resources.getDimensionPixelSize(R.dimen.height_barcode)

        image.setImageBitmap(
            createBarcodeBitmap(
                barcodeValue = value,
                barcodeColor = getColor(context!!, R.color.colorPrimary),
                backgroundColor = getColor(context!!, android.R.color.white),
                widthPixels = widthPixels,
                heightPixels = heightPixels
            )
        )
        text.text = value
    }
}