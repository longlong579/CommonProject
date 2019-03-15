package self.xhl.com.commonproject.fragmentLazyLoadTest

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_blank.*
import self.xhl.com.commonproject.R
import self.xhl.com.commonproject.kotlinextension.singleToast


class LazyLoadFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            mParam1 = getString(ARG_PARAM1)
            mParam2 = getString(ARG_PARAM2)
        }
        Logger.d(this.javaClass.name + mParam1 + "：onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        Logger.d(mParam1 + "：onCreateView")

        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textF.text = mParam1;

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            //throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
        Logger.d(this.javaClass.name + "：onAttach")

    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        Logger.d(this.javaClass.name + mParam1 + "：onDetach")

    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): LazyLoadFragment {
            val fragment = LazyLoadFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            delayInit()
        }
        Logger.d(this.javaClass.name + mParam1 + "  :" + hidden)
    }


    private var canLoad = true
    fun delayInit() {
        if (!canLoad) {
            return
        }
        canLoad = false
        singleToast("第一次加载数据：" + mParam1)
        Logger.d(this.javaClass.name + mParam1 + "  :开始加载")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.d(this.javaClass.name + mParam1 + "：onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d(this.javaClass.name + mParam1 + "：OnDestroy")
    }
}
