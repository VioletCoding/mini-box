package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Violet
 * @description 菜单实体
 * @date 2021/1/12
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MbParentMenu {
    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty("菜单名称")
    private String menuName;
    @ApiModelProperty("菜单图片，去Ant的icon组件里面找，写图标名字")
    private String menuIcon;
    @ApiModelProperty("子菜单列表")
    private List<MbSubMenu> subMenuList;
}
