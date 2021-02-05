package com.ghw.minibox.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Violet
 * @description 菜单实体
 * @date 2021/2/5
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("mb_menu")
public class MenuModel implements Serializable {
    private static final long serialVersionUID = -657496885197972L;
    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty("菜单名")
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;
    @ApiModelProperty("菜单图标")
    private String menuIcon;
    @ApiModelProperty("菜单路由")
    private String menuUrl;
}
