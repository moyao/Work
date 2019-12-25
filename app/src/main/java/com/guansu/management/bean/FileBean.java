package com.guansu.management.bean;

import java.util.List;

public class FileBean {


    /**
     * code : 0000000
     * data : {"imageList":[{"image":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/d7e6f43743c06b113a8a20a1c31547de_1440×2560.jpg","isCover":true},{"image":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/665a5b1b9f28dec885f453ec821db144_1440×2560.jpg","isCover":false},{"image":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/f20c86460a828c031e40e87e429ac9d4_1440×2560.jpg","isCover":false},{"image":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/d1d59765791039a64afce91cb6295094_1440×2560.jpg","isCover":false}]}
     * developMsg :
     * msg : 请求成功
     * ts : 1576749356797
     * uri :
     */

    private String code;
    private DataBean data;
    private String developMsg;
    private String msg;
    private long ts;
    private String uri;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getDevelopMsg() {
        return developMsg;
    }

    public void setDevelopMsg(String developMsg) {
        this.developMsg = developMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public static class DataBean {
        private List<ImageListBean> imageList;

        public List<ImageListBean> getImageList() {
            return imageList;
        }

        public void setImageList(List<ImageListBean> imageList) {
            this.imageList = imageList;
        }

        public static class ImageListBean {
            /**
             * image : http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/d7e6f43743c06b113a8a20a1c31547de_1440×2560.jpg
             * isCover : true
             */

            private String image;
            private boolean isCover;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public boolean isIsCover() {
                return isCover;
            }

            public void setIsCover(boolean isCover) {
                this.isCover = isCover;
            }

            @Override
            public String toString() {
                return "ImageListBean{" +
                        "image='" + image + '\'' +
                        ", isCover=" + isCover +
                        '}';
            }
        }
    }
}
