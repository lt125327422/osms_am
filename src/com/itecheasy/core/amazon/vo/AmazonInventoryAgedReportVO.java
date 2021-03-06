package com.itecheasy.core.amazon.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Auther: liteng
 * @Date: 2018/8/9 18:15
 * @Description:
 */
public class AmazonInventoryAgedReportVO {

    private Integer id;
    private Integer shopId;

    private String sku;
    //考虑是否要把cmsCode也加上

    private Date syncFirst; //这个记录上一次在resultReportTable中存在的日期
    private Date syncLast;  //更新日期

    private Date snapshotDate;  //亚马逊给的同步日期

    private Boolean isClearanceItem;    //是否需清仓

    /**
     * 进入90~180天的时间（对于老商品，不知道什么时候进入90-180天。上线时，记当天即可）
     *
     * 这个值，一般不会改。唯一修改的情况是，判断更新日期不是前一天
     */
    private Date startDate;         //起始日期

    /**
     * 1.	值=下一清算点-8
     * 2.	唯一修改的情况不更新，是，判断更新日期不是前一天
     */
    private Date terminationDate;   //终止日期

    /**
     * 1.值=当前日期+可售库存/30天销量*30（进1法）
     * 2.如果30天销量为0，则2050-1-1（日期比较远，一看就卖不完）
     */
    private Date planStockToZeroDate;   //预计库存清零日期

    /**
     * 1.	意思是，还需要用多少天才能卖完
     * 2.	值=（可用库存/30天销量）*30（小数）
     * 3.	如果30天销量为0，则取值99999
     */
    private BigDecimal sellOutDate; //售完时间

    /**
     * 1.	终止日期=<预计库存清0日期，则无法售完
     * 2.	终止日期>预计库存清0日期，则可以售完
     */
    private Integer sellOutStatus;  //售完状态  0不能售完   1可以售完   2已

    /**
     * 1.这个值，跟起始日期是成对出现的。而且需要在起始日期有更新，它才更新。
     * 2.计算起始日期 +90
     * 如果值<=15号，则取“【起始日期】+90” 这个结果对应的月的15号
     * 如果值>15号，则取下一个月的15号 也就是在90的基础上再增加30
     */
    private Date nextClearingPoint; //下一清算点



    //下面这些都是从亚马逊同步过来的商品信息

    /**
     * 1.读当天库存历史报告。
     * 2.唯一修改的情况不更新，是，判断更新日期不是前一天
     */
    private BigDecimal avaliableQuantitySellable;   //可售库存

    /**
     * 从同步过来的近30天的销量
     */
    private BigDecimal unitsShippedLast30Days;

    private BigDecimal invAge0To90Days;
    private BigDecimal invAge91To180Days;
    private BigDecimal invAge181To270Days;
    private BigDecimal invAge271To365Days;
    private BigDecimal invAge365PlusDays;

    //其他暂时不重要信息
    private String backlogAlertWarning; //积压预警  给业务导出库龄报表用,这边是空着的
    private Boolean isDoingClearance;   //是否在清仓   给业务导出库龄报表用,这边是空着的

    private String fnsku;
    private String asin;
    private String productName;
    private String condition;
    private BigDecimal qtyWithRemovalsInProgress;
    private String currency;
    private BigDecimal qtyToBeChargedLtsf6Mo;
    private BigDecimal projectedLtsf6Mo;
    private BigDecimal qtyToBeChargedLtsf12Mo;
    private BigDecimal projectedLtsf12Mo;
    private BigDecimal unitsShippedLast7Days;

    private BigDecimal unitsShippedLast60Days;
    private BigDecimal unitsShippedLast90Days;
    private String alert;
    private BigDecimal yourPrice;
    private BigDecimal salesPrice;
    private BigDecimal lowestPriceNew;
    private BigDecimal lowestPriceUsed;
    private String recommendedAction;
    private BigDecimal healthyInventoryLevel;
    private BigDecimal recommendedSalesPrice;
    private BigDecimal recommendedSaleDurationDays;
    private BigDecimal recommendedRemovalvQuantity;
    private BigDecimal estimatedCostSavingsOfRemoval;
    private BigDecimal sellThrough;
    private BigDecimal cubicFeet;
    private String storageType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Date getSyncFirst() {
        return syncFirst;
    }

    public void setSyncFirst(Date syncFirst) {
        this.syncFirst = syncFirst;
    }

    public Date getSyncLast() {
        return syncLast;
    }

    public void setSyncLast(Date syncLast) {
        this.syncLast = syncLast;
    }

    public Date getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(Date snapshotDate) {
        this.snapshotDate = snapshotDate;
    }

    public Boolean getClearanceItem() {
        return isClearanceItem;
    }

    public void setClearanceItem(Boolean clearanceItem) {
        isClearanceItem = clearanceItem;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Date getPlanStockToZeroDate() {
        return planStockToZeroDate;
    }

    public void setPlanStockToZeroDate(Date planStockToZeroDate) {
        this.planStockToZeroDate = planStockToZeroDate;
    }

    public BigDecimal getSellOutDate() {
        return sellOutDate;
    }

    public void setSellOutDate(BigDecimal sellOutDate) {
        this.sellOutDate = sellOutDate;
    }

    public Integer getSellOutStatus() {
        return sellOutStatus;
    }

    public void setSellOutStatus(Integer sellOutStatus) {
        this.sellOutStatus = sellOutStatus;
    }

    public Date getNextClearingPoint() {
        return nextClearingPoint;
    }

    public void setNextClearingPoint(Date nextClearingPoint) {
        this.nextClearingPoint = nextClearingPoint;
    }

    public BigDecimal getAvaliableQuantitySellable() {
        return avaliableQuantitySellable;
    }

    public void setAvaliableQuantitySellable(BigDecimal avaliableQuantitySellable) {
        this.avaliableQuantitySellable = avaliableQuantitySellable;
    }

    public BigDecimal getUnitsShippedLast30Days() {
        return unitsShippedLast30Days;
    }

    public void setUnitsShippedLast30Days(BigDecimal unitsShippedLast30Days) {
        this.unitsShippedLast30Days = unitsShippedLast30Days;
    }

    public BigDecimal getInvAge0To90Days() {
        return invAge0To90Days;
    }

    public void setInvAge0To90Days(BigDecimal invAge0To90Days) {
        this.invAge0To90Days = invAge0To90Days;
    }

    public BigDecimal getInvAge91To180Days() {
        return invAge91To180Days;
    }

    public void setInvAge91To180Days(BigDecimal invAge91To180Days) {
        this.invAge91To180Days = invAge91To180Days;
    }

    public BigDecimal getInvAge181To270Days() {
        return invAge181To270Days;
    }

    public void setInvAge181To270Days(BigDecimal invAge181To270Days) {
        this.invAge181To270Days = invAge181To270Days;
    }

    public BigDecimal getInvAge271To365Days() {
        return invAge271To365Days;
    }

    public void setInvAge271To365Days(BigDecimal invAge271To365Days) {
        this.invAge271To365Days = invAge271To365Days;
    }

    public BigDecimal getInvAge365PlusDays() {
        return invAge365PlusDays;
    }

    public void setInvAge365PlusDays(BigDecimal invAge365PlusDays) {
        this.invAge365PlusDays = invAge365PlusDays;
    }

    public String getBacklogAlertWarning() {
        return backlogAlertWarning;
    }

    public void setBacklogAlertWarning(String backlogAlertWarning) {
        this.backlogAlertWarning = backlogAlertWarning;
    }

    public Boolean getDoingClearance() {
        return isDoingClearance;
    }

    public void setDoingClearance(Boolean doingClearance) {
        isDoingClearance = doingClearance;
    }

    public String getFnsku() {
        return fnsku;
    }

    public void setFnsku(String fnsku) {
        this.fnsku = fnsku;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public BigDecimal getQtyWithRemovalsInProgress() {
        return qtyWithRemovalsInProgress;
    }

    public void setQtyWithRemovalsInProgress(BigDecimal qtyWithRemovalsInProgress) {
        this.qtyWithRemovalsInProgress = qtyWithRemovalsInProgress;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getQtyToBeChargedLtsf6Mo() {
        return qtyToBeChargedLtsf6Mo;
    }

    public void setQtyToBeChargedLtsf6Mo(BigDecimal qtyToBeChargedLtsf6Mo) {
        this.qtyToBeChargedLtsf6Mo = qtyToBeChargedLtsf6Mo;
    }

    public BigDecimal getProjectedLtsf6Mo() {
        return projectedLtsf6Mo;
    }

    public void setProjectedLtsf6Mo(BigDecimal projectedLtsf6Mo) {
        this.projectedLtsf6Mo = projectedLtsf6Mo;
    }

    public BigDecimal getQtyToBeChargedLtsf12Mo() {
        return qtyToBeChargedLtsf12Mo;
    }

    public void setQtyToBeChargedLtsf12Mo(BigDecimal qtyToBeChargedLtsf12Mo) {
        this.qtyToBeChargedLtsf12Mo = qtyToBeChargedLtsf12Mo;
    }

    public BigDecimal getProjectedLtsf12Mo() {
        return projectedLtsf12Mo;
    }

    public void setProjectedLtsf12Mo(BigDecimal projectedLtsf12Mo) {
        this.projectedLtsf12Mo = projectedLtsf12Mo;
    }

    public BigDecimal getUnitsShippedLast7Days() {
        return unitsShippedLast7Days;
    }

    public void setUnitsShippedLast7Days(BigDecimal unitsShippedLast7Days) {
        this.unitsShippedLast7Days = unitsShippedLast7Days;
    }

    public BigDecimal getUnitsShippedLast60Days() {
        return unitsShippedLast60Days;
    }

    public void setUnitsShippedLast60Days(BigDecimal unitsShippedLast60Days) {
        this.unitsShippedLast60Days = unitsShippedLast60Days;
    }

    public BigDecimal getUnitsShippedLast90Days() {
        return unitsShippedLast90Days;
    }

    public void setUnitsShippedLast90Days(BigDecimal unitsShippedLast90Days) {
        this.unitsShippedLast90Days = unitsShippedLast90Days;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public BigDecimal getYourPrice() {
        return yourPrice;
    }

    public void setYourPrice(BigDecimal yourPrice) {
        this.yourPrice = yourPrice;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public BigDecimal getLowestPriceNew() {
        return lowestPriceNew;
    }

    public void setLowestPriceNew(BigDecimal lowestPriceNew) {
        this.lowestPriceNew = lowestPriceNew;
    }

    public BigDecimal getLowestPriceUsed() {
        return lowestPriceUsed;
    }

    public void setLowestPriceUsed(BigDecimal lowestPriceUsed) {
        this.lowestPriceUsed = lowestPriceUsed;
    }

    public String getRecommendedAction() {
        return recommendedAction;
    }

    public void setRecommendedAction(String recommendedAction) {
        this.recommendedAction = recommendedAction;
    }

    public BigDecimal getHealthyInventoryLevel() {
        return healthyInventoryLevel;
    }

    public void setHealthyInventoryLevel(BigDecimal healthyInventoryLevel) {
        this.healthyInventoryLevel = healthyInventoryLevel;
    }

    public BigDecimal getRecommendedSalesPrice() {
        return recommendedSalesPrice;
    }

    public void setRecommendedSalesPrice(BigDecimal recommendedSalesPrice) {
        this.recommendedSalesPrice = recommendedSalesPrice;
    }

    public BigDecimal getRecommendedSaleDurationDays() {
        return recommendedSaleDurationDays;
    }

    public void setRecommendedSaleDurationDays(BigDecimal recommendedSaleDurationDays) {
        this.recommendedSaleDurationDays = recommendedSaleDurationDays;
    }

    public BigDecimal getRecommendedRemovalvQuantity() {
        return recommendedRemovalvQuantity;
    }

    public void setRecommendedRemovalvQuantity(BigDecimal recommendedRemovalvQuantity) {
        this.recommendedRemovalvQuantity = recommendedRemovalvQuantity;
    }

    public BigDecimal getEstimatedCostSavingsOfRemoval() {
        return estimatedCostSavingsOfRemoval;
    }

    public void setEstimatedCostSavingsOfRemoval(BigDecimal estimatedCostSavingsOfRemoval) {
        this.estimatedCostSavingsOfRemoval = estimatedCostSavingsOfRemoval;
    }

    public BigDecimal getSellThrough() {
        return sellThrough;
    }

    public void setSellThrough(BigDecimal sellThrough) {
        this.sellThrough = sellThrough;
    }

    public BigDecimal getCubicFeet() {
        return cubicFeet;
    }

    public void setCubicFeet(BigDecimal cubicFeet) {
        this.cubicFeet = cubicFeet;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }


    @Override
    public String toString() {
        return "AmazonInventoryAgedReportVO{" +
                "id=" + id +
                ", shopId=" + shopId +
                ", sku='" + sku + '\'' +
                ", syncFirst=" + syncFirst +
                ", syncLast=" + syncLast +
                ", snapshotDate=" + snapshotDate +
                ", isClearanceItem=" + isClearanceItem +
                ", startDate=" + startDate +
                ", terminationDate=" + terminationDate +
                ", planStockToZeroDate=" + planStockToZeroDate +
                ", sellOutDate=" + sellOutDate +
                ", sellOutStatus=" + sellOutStatus +
                ", nextClearingPoint=" + nextClearingPoint +
                ", avaliableQuantitySellable=" + avaliableQuantitySellable +
                ", unitsShippedLast30Days=" + unitsShippedLast30Days +
                ", invAge0To90Days=" + invAge0To90Days +
                ", invAge91To180Days=" + invAge91To180Days +
                ", invAge181To270Days=" + invAge181To270Days +
                ", invAge271To365Days=" + invAge271To365Days +
                ", invAge365PlusDays=" + invAge365PlusDays +
                ", backlogAlertWarning='" + backlogAlertWarning + '\'' +
                ", isDoingClearance=" + isDoingClearance +
                ", fnsku='" + fnsku + '\'' +
                ", asin='" + asin + '\'' +
                ", productName='" + productName + '\'' +
                ", condition='" + condition + '\'' +
                ", qtyWithRemovalsInProgress=" + qtyWithRemovalsInProgress +
                ", currency='" + currency + '\'' +
                ", qtyToBeChargedLtsf6Mo=" + qtyToBeChargedLtsf6Mo +
                ", projectedLtsf6Mo=" + projectedLtsf6Mo +
                ", qtyToBeChargedLtsf12Mo=" + qtyToBeChargedLtsf12Mo +
                ", projectedLtsf12Mo=" + projectedLtsf12Mo +
                ", unitsShippedLast7Days=" + unitsShippedLast7Days +
                ", unitsShippedLast60Days=" + unitsShippedLast60Days +
                ", unitsShippedLast90Days=" + unitsShippedLast90Days +
                ", alert='" + alert + '\'' +
                ", yourPrice=" + yourPrice +
                ", salesPrice=" + salesPrice +
                ", lowestPriceNew=" + lowestPriceNew +
                ", lowestPriceUsed=" + lowestPriceUsed +
                ", recommendedAction='" + recommendedAction + '\'' +
                ", healthyInventoryLevel=" + healthyInventoryLevel +
                ", recommendedSalesPrice=" + recommendedSalesPrice +
                ", recommendedSaleDurationDays=" + recommendedSaleDurationDays +
                ", recommendedRemovalvQuantity=" + recommendedRemovalvQuantity +
                ", estimatedCostSavingsOfRemoval=" + estimatedCostSavingsOfRemoval +
                ", sellThrough=" + sellThrough +
                ", cubicFeet=" + cubicFeet +
                ", storageType='" + storageType + '\'' +
                '}';
    }
}
