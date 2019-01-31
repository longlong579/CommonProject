package self.xhl.com.common.dialog.bottom_sheet_dialogfragment


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import self.xhl.com.common.R


/**
 * A simple [Fragment] subclass.
 * Use the [SelectImageTypeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectImageTypeFragment : BottomSheetDialogFragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MyBottomSheetDialog(context!!, theme)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mParam1 = it.getString(ARG_PARAM1)
            mParam2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_select_pic_camara, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun onButtonPressed(uri: String) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            mListener = context
//        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onStart() {
        super.onStart()

    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

    //    @OnClick({R.id.tv_take_photo, R.id.tv_gallery, R.id.tv_cancel})
    //    public void onViewClicked(View view) {
    //        switch (view.getId()) {
    //            case R.id.tv_take_photo:
    //                onButtonPressed(ACTION_TAKE_PHOTO);
    //                break;
    //            case R.id.tv_gallery:
    //                onButtonPressed(ACTION_GALLERY_PHOTO);
    //                break;
    //            case R.id.tv_cancel:
    //                break;
    //            default:
    //                break;
    //        }
    //        dismiss();
    //    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(actionStr: String)
    }

    companion object {
        //RequestCode
        @JvmField
        val ACTION_TAKE_PHOTO = "take_photo"
        @JvmField
        val ACTION_GALLERY_PHOTO = "gallery_photo"

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String): SelectImageTypeFragment {
            val fragment = SelectImageTypeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
