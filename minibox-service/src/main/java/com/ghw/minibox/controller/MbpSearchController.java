package com.ghw.minibox.controller;

import com.ghw.minibox.es.GameSearch;
import com.ghw.minibox.es.PostSearch;
import com.ghw.minibox.model.GameModel;
import com.ghw.minibox.model.PostModel;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author Violet
 * @description 搜索控制器
 * @date 2021/2/13
 */
@RestController
@RequestMapping("searchApi")
public class MbpSearchController {
    @Resource
    private PostSearch postSearch;
    @Resource
    private GameSearch gameSearch;

    @GetMapping("search")
    public ResultVo searchPostAndGame(@RequestParam String keyword) {
        List<PostModel> postModels = postSearch.findPostModelByTitleOrContent(keyword, keyword);
        List<GameModel> gameModels = gameSearch.findGameModelByNameOrDeveloperOrPublisher(keyword, keyword, keyword);

        if (postModels.size() == 0 || gameModels.size() == 0) {
            return Result.success("没有搜索结果");
        }
        // TODO 记得更新ES

        HashMap<String, Object> map = new HashMap<>();
        map.put("post", postModels);
        map.put("game", gameModels);
        return Result.success(map);
    }
}
