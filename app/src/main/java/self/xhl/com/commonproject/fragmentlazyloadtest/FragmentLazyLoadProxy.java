package self.xhl.com.commonproject.fragmentlazyloadtest;

/**
 * @author xhl
 * Activicy add模式 ViewPager模式下共用
 * 注意：add模式下，需要先comit一次才会有效
 * @desc 2019/4/2 15:25
 */
public class FragmentLazyLoadProxy {
    //注意：shouldLazyLoad/fragmentHidden 不要在异常时存储与处理 每次后台内存异常时，让其默认初始化
    //Activity add模式下
    private boolean shouldLazyLoad = true;//是否需要去延时加载
    private boolean fragmentHidden = true;//add模式下Fragment的显影


    public void onDestroyView()
    {}

    public void onDestroy()
    {

    }
    interface ILazyLoad
    {

    }
}
