package cn.itcast.travel.domain;

public class Verified {

    private Integer vid;  //主键
    private Integer uid;  // 用户ID
    private String fullName; //姓名
    private String ethnic;  //民族
    private String address;  //住址
    private String idCardNum;  //公民身份证号码
    private String birthday;  //出生
    private String sex;  //性别
    private String expirationDate; //身份证失效日期
    private String issuingAuthority; //签发机关
    private String dateOfIssue; //签发日期


    /**
     * 正面图片状态
     * normal-识别正常
     * reversed_side-身份证正反面颠倒
     * non_idcard-上传的图片中不包含身份证
     * blurred-身份证模糊
     * other_type_card-其他类型证照
     * over_exposure-身份证关键字段反光或过曝
     * over_dark-身份证欠曝（亮度过低）
     * unknown-未知状态
     */
    private String frontImageStatus;

    /**
     * 正面图片类型
     * normal-正常身份证；copy-复印件；temporary-临时身份证；screen-翻拍；unknown-其他未知情况
     */
    private String frontRiskType;


    /**
     * 反面图片状态
     * normal-识别正常
     * reversed_side-身份证正反面颠倒
     * non_idcard-上传的图片中不包含身份证
     * blurred-身份证模糊
     * other_type_card-其他类型证照
     * over_exposure-身份证关键字段反光或过曝
     * over_dark-身份证欠曝（亮度过低）
     * unknown-未知状态
     */
    private String backImageStatus;

    /**
     * 反面图片类型
     * normal-正常身份证；copy-复印件；temporary-临时身份证；screen-翻拍；unknown-其他未知情况
     */
    private String backRiskType;

    /**
     * 身份证号码、性别、出生是否一致
     * <p>
     * -1: 身份证正面所有字段全为空
     * 0: 身份证证号不合法，此情况下不返回身份证证号
     * 1: 身份证证号和性别、出生信息一致
     * 2: 身份证证号和性别、出生信息都不一致
     * 3: 身份证证号和出生信息不一致
     * 4: 身份证证号和性别信息不一致
     */
    private String idCardNumberType;

    private boolean passed; //是否已经实名认证通过  true 通过 false未通过

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }


    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getFrontImageStatus() {
        return frontImageStatus;
    }

    public void setFrontImageStatus(String frontImageStatus) {
        this.frontImageStatus = frontImageStatus;
    }

    public String getFrontRiskType() {
        return frontRiskType;
    }

    public void setFrontRiskType(String frontRiskType) {
        this.frontRiskType = frontRiskType;
    }

    public String getBackImageStatus() {
        return backImageStatus;
    }

    public void setBackImageStatus(String backImageStatus) {
        this.backImageStatus = backImageStatus;
    }

    public String getBackRiskType() {
        return backRiskType;
    }

    public void setBackRiskType(String backRiskType) {
        this.backRiskType = backRiskType;
    }

    public String getIdCardNumberType() {
        return idCardNumberType;
    }

    public void setIdCardNumberType(String idCardNumberType) {
        this.idCardNumberType = idCardNumberType;
    }


    @Override
    public String toString() {
        return "Verified{" +
                "vid=" + vid +
                ", uid=" + uid +
                ", fullName='" + fullName + '\'' +
                ", ethnic='" + ethnic + '\'' +
                ", address='" + address + '\'' +
                ", idCardNum='" + idCardNum + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", issuingAuthority='" + issuingAuthority + '\'' +
                ", dateOfIssue='" + dateOfIssue + '\'' +
                ", frontImageStatus='" + frontImageStatus + '\'' +
                ", frontRiskType='" + frontRiskType + '\'' +
                ", backImageStatus='" + backImageStatus + '\'' +
                ", backRiskType='" + backRiskType + '\'' +
                ", idCardNumberType='" + idCardNumberType + '\'' +
                ", passed=" + passed +
                '}';
    }
}
