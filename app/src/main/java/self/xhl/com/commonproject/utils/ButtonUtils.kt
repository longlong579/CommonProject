package self.xhl.com.commonproject.utils

/*
   *多次按钮点击判断  gv_isf.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
          long arg3) {
        if (!ButtonUtils.isFastDoubleClick(R.id.gv_integralstore)) {
          //写你相关操作即可
        }
      }
    });
   */
object ButtonUtils {
    private var lastClickTime: Long = 0
    private val DIFF: Long = 1000
    private var lastButtonId = -1

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     */
    fun isFastDoubleClick(buttonId: Int): Boolean {
        return isFastDoubleClick(buttonId, DIFF)
    }

    private fun isFastDoubleClick(): Boolean {
        return isFastDoubleClick(-1, DIFF)
    }

    private fun isFastDoubleClick(buttonId: Int, diff: Long): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - lastClickTime
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            // Log.v("isFastDoubleClick", "短时间内按钮多次触发")
            return true
        }
        lastClickTime = time
        lastButtonId = buttonId
        return false
    }
}