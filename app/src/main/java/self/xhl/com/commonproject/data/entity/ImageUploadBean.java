package self.xhl.com.commonproject.data.entity;


import java.io.Serializable;

/**
 * Created by xhl on 2018/09/09.
 */


public class ImageUploadBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public String fileName;//	 	文件名称
    public String fileNameGetFroQnu;//	 	从七牛云上获取的文件名称
    public String filePath;// 	 	文件路径
    public int pic_isupload;//      上传状态 1：已上传  2：上传中  3：上传失败
    public String imageType;//    银行卡正面 反面
    public int imageBelong;//图片所属RecycleView 0:银行卡 1：协议
    public boolean isAdd;
    public boolean isSelected;
    public boolean isShowTitle;
    public boolean isCancelled=false;

    public String getStateString()
    {
        switch (pic_isupload) {
            case 1:return "已上传";
            case 2:return "上传中";
            case 3:return "上传失败";
            default:return null;
        }
    }
}
