package com.ebookfrenzy.clipmycard.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ebookfrenzy.clipmycard.R
import com.ebookfrenzy.clipmycard.models.Card

open class YesOrNoFragment(var yesClick: (Card) -> Unit, var noClick: () -> Unit, card: Card) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //Here we create a new dialogbuilder;
        val alert = AlertDialog.Builder(
            activity)
        alert.setTitle(R.string.confirmation)
        alert.setMessage(R.string.areYouSure)
        alert.setPositiveButton(R.string.yes, yListener)
        alert.setNegativeButton(R.string.no, nListener)

        return alert.create()
    }
    //This is our positive listener for when the user presses
    //the yes button
    private var yListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { _, _ ->
        // these will be executed when user click Yes button
        yesClick(card)
    }

    //This is our negative listener for when the user presses
    //the no button.
    private var nListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { _, _ ->
        // these will be executed when user click No button
        noClick()
    }
}