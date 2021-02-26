package com.yuxh.inyaa.web.plumemo.dashboard.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: byteblogs
 * @date: 2020/1/16 21:32
 */
@Data
@Accessors(chain = true)
public class ViewChartVO {

    private java.util.List<ViewChartSpotVO> viewRecordList;

}
