package com.golang.management.bean;

import java.util.List;

/**
 * @date:
 * @author: dongyaoyao
 */
public class DistributiopnHomepageBean {
        /**
         * number : 0
         * last : true
         * size : 10
         * numberOfElements : 1
         * totalPages : 1
         * pageable : {"paged":true,"pageNumber":0,"offset":0,"pageSize":10,"unpaged":false,"sort":{"unsorted":false,"sorted":true,"empty":false}}
         * sort : {"unsorted":false,"sorted":true,"empty":false}
         * content : [{"userInfoDto":{"realName":null,"userLevel":{"levelCode":"L00026","levelPicture":"","enable":true,"levelDiscount":10,"sortOrder":1,"levelName":"普通用户","id":1169151778160119808},"mobileNumber":null,"sex":"MALE","nickname":"13992076915","id":1207114838900215808,"accountSalt":null,"profileImageUrl":"http://cdn.kdtonline.cn/faker_avator/piliang/6.jpg","age":null},"createdAt":"2019-12-23 17:59","encryption":"453011bb95e8c2894f42c3e74e9d3c10","updatePayTime":1577095182625,"id":"1209050943321018368","accountBalance":11278,"userId":1207114838900215808,"updatedAt":"2019-12-25 14:16"}]
         * first : true
         * totalElements : 1
         * empty : false
         */

        private int number;
        private boolean last;
        private int size;
        private int numberOfElements;
        private int totalPages;
        private PageableBean pageable;
        private SortBeanX sort;
        private boolean first;
        private int totalElements;
        private boolean empty;
        private List<ContentBean> content;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public PageableBean getPageable() {
            return pageable;
        }

        public void setPageable(PageableBean pageable) {
            this.pageable = pageable;
        }

        public SortBeanX getSort() {
            return sort;
        }

        public void setSort(SortBeanX sort) {
            this.sort = sort;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public boolean isEmpty() {
            return empty;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class PageableBean {
            /**
             * paged : true
             * pageNumber : 0
             * offset : 0
             * pageSize : 10
             * unpaged : false
             * sort : {"unsorted":false,"sorted":true,"empty":false}
             */

            private boolean paged;
            private int pageNumber;
            private int offset;
            private int pageSize;
            private boolean unpaged;
            private SortBean sort;

            public boolean isPaged() {
                return paged;
            }

            public void setPaged(boolean paged) {
                this.paged = paged;
            }

            public int getPageNumber() {
                return pageNumber;
            }

            public void setPageNumber(int pageNumber) {
                this.pageNumber = pageNumber;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public boolean isUnpaged() {
                return unpaged;
            }

            public void setUnpaged(boolean unpaged) {
                this.unpaged = unpaged;
            }

            public SortBean getSort() {
                return sort;
            }

            public void setSort(SortBean sort) {
                this.sort = sort;
            }

            public static class SortBean {
                /**
                 * unsorted : false
                 * sorted : true
                 * empty : false
                 */

                private boolean unsorted;
                private boolean sorted;
                private boolean empty;

                public boolean isUnsorted() {
                    return unsorted;
                }

                public void setUnsorted(boolean unsorted) {
                    this.unsorted = unsorted;
                }

                public boolean isSorted() {
                    return sorted;
                }

                public void setSorted(boolean sorted) {
                    this.sorted = sorted;
                }

                public boolean isEmpty() {
                    return empty;
                }

                public void setEmpty(boolean empty) {
                    this.empty = empty;
                }
            }
        }

        public static class SortBeanX {
            /**
             * unsorted : false
             * sorted : true
             * empty : false
             */

            private boolean unsorted;
            private boolean sorted;
            private boolean empty;

            public boolean isUnsorted() {
                return unsorted;
            }

            public void setUnsorted(boolean unsorted) {
                this.unsorted = unsorted;
            }

            public boolean isSorted() {
                return sorted;
            }

            public void setSorted(boolean sorted) {
                this.sorted = sorted;
            }

            public boolean isEmpty() {
                return empty;
            }

            public void setEmpty(boolean empty) {
                this.empty = empty;
            }
        }

        public static class ContentBean {
            /**
             * userInfoDto : {"realName":null,"userLevel":{"levelCode":"L00026","levelPicture":"","enable":true,"levelDiscount":10,"sortOrder":1,"levelName":"普通用户","id":1169151778160119808},"mobileNumber":null,"sex":"MALE","nickname":"13992076915","id":1207114838900215808,"accountSalt":null,"profileImageUrl":"http://cdn.kdtonline.cn/faker_avator/piliang/6.jpg","age":null}
             * createdAt : 2019-12-23 17:59
             * encryption : 453011bb95e8c2894f42c3e74e9d3c10
             * updatePayTime : 1577095182625
             * id : 1209050943321018368
             * accountBalance : 11278.0
             * userId : 1207114838900215808
             * updatedAt : 2019-12-25 14:16
             */

            private UserInfoDtoBean userInfoDto;
            private String createdAt;
            private String encryption;
            private long updatePayTime;
            private String id;
            private double accountBalance;
            private long userId;
            private String updatedAt;

            public UserInfoDtoBean getUserInfoDto() {
                return userInfoDto;
            }

            public void setUserInfoDto(UserInfoDtoBean userInfoDto) {
                this.userInfoDto = userInfoDto;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getEncryption() {
                return encryption;
            }

            public void setEncryption(String encryption) {
                this.encryption = encryption;
            }

            public long getUpdatePayTime() {
                return updatePayTime;
            }

            public void setUpdatePayTime(long updatePayTime) {
                this.updatePayTime = updatePayTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public double getAccountBalance() {
                return accountBalance;
            }

            public void setAccountBalance(double accountBalance) {
                this.accountBalance = accountBalance;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public static class UserInfoDtoBean {
                /**
                 * realName : null
                 * userLevel : {"levelCode":"L00026","levelPicture":"","enable":true,"levelDiscount":10,"sortOrder":1,"levelName":"普通用户","id":1169151778160119808}
                 * mobileNumber : null
                 * sex : MALE
                 * nickname : 13992076915
                 * id : 1207114838900215808
                 * accountSalt : null
                 * profileImageUrl : http://cdn.kdtonline.cn/faker_avator/piliang/6.jpg
                 * age : null
                 */

                private Object realName;
                private UserLevelBean userLevel;
                private Object mobileNumber;
                private String sex;
                private String nickname;
                private long id;
                private Object accountSalt;
                private String profileImageUrl;
                private Object age;

                public Object getRealName() {
                    return realName;
                }

                public void setRealName(Object realName) {
                    this.realName = realName;
                }

                public UserLevelBean getUserLevel() {
                    return userLevel;
                }

                public void setUserLevel(UserLevelBean userLevel) {
                    this.userLevel = userLevel;
                }

                public Object getMobileNumber() {
                    return mobileNumber;
                }

                public void setMobileNumber(Object mobileNumber) {
                    this.mobileNumber = mobileNumber;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public Object getAccountSalt() {
                    return accountSalt;
                }

                public void setAccountSalt(Object accountSalt) {
                    this.accountSalt = accountSalt;
                }

                public String getProfileImageUrl() {
                    return profileImageUrl;
                }

                public void setProfileImageUrl(String profileImageUrl) {
                    this.profileImageUrl = profileImageUrl;
                }

                public Object getAge() {
                    return age;
                }

                public void setAge(Object age) {
                    this.age = age;
                }

                public static class UserLevelBean {
                    /**
                     * levelCode : L00026
                     * levelPicture :
                     * enable : true
                     * levelDiscount : 10.0
                     * sortOrder : 1
                     * levelName : 普通用户
                     * id : 1169151778160119808
                     */

                    private String levelCode;
                    private String levelPicture;
                    private boolean enable;
                    private double levelDiscount;
                    private int sortOrder;
                    private String levelName;
                    private long id;

                    public String getLevelCode() {
                        return levelCode;
                    }

                    public void setLevelCode(String levelCode) {
                        this.levelCode = levelCode;
                    }

                    public String getLevelPicture() {
                        return levelPicture;
                    }

                    public void setLevelPicture(String levelPicture) {
                        this.levelPicture = levelPicture;
                    }

                    public boolean isEnable() {
                        return enable;
                    }

                    public void setEnable(boolean enable) {
                        this.enable = enable;
                    }

                    public double getLevelDiscount() {
                        return levelDiscount;
                    }

                    public void setLevelDiscount(double levelDiscount) {
                        this.levelDiscount = levelDiscount;
                    }

                    public int getSortOrder() {
                        return sortOrder;
                    }

                    public void setSortOrder(int sortOrder) {
                        this.sortOrder = sortOrder;
                    }

                    public String getLevelName() {
                        return levelName;
                    }

                    public void setLevelName(String levelName) {
                        this.levelName = levelName;
                    }

                    public long getId() {
                        return id;
                    }

                    public void setId(long id) {
                        this.id = id;
                    }
                }
            }
        }

}
