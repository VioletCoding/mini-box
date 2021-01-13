package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Violet
 * @description 子菜单实例
 * @date 2021/1/12
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MbSubMenu {
    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty("子菜单名称")
    private String subMenuName;
    @ApiModelProperty("子菜单路由")
    private String subMenuRouteUrl;
    @ApiModelProperty("父菜单id")
    private Long parentMenuId;
}
