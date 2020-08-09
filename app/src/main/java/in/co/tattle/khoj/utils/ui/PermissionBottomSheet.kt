package `in`.co.tattle.khoj.ui.message.create.screenshot

import `in`.co.tattle.khoj.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.permission_bottom_sheet.*

class PermissionBottomSheet(
    val permissionSheetCallback: (permissionGranted: Boolean, requestCode: Int) -> Unit,
    private val requestCode: Int
) :
    BottomSheetDialogFragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.permission_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false

        btnOk.setOnClickListener(this)
        btnCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.btnOk) {
            permissionSheetCallback(true, requestCode)
            this.dismiss()
        } else if (v.id == R.id.btnCancel) {
            permissionSheetCallback(false, requestCode)
            this.dismiss()
        }
    }

}