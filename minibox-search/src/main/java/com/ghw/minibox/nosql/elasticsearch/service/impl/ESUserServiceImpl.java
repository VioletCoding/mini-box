package com.ghw.minibox.nosql.elasticsearch.service.impl;

import com.ghw.minibox.document.ESUser;
import com.ghw.minibox.feign.ProducerFeign;
import com.ghw.minibox.nosql.elasticsearch.repository.ESUserRepository;
import com.ghw.minibox.nosql.elasticsearch.service.ESUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Violet
 * @description ESUserService实现类
 * @date 2020/11/19
 */
@Service
@Slf4j
public class ESUserServiceImpl implements ESUserService {
    @Resource
    private ESUserRepository esUserRepository;
    @Resource
    private ProducerFeign producerFeign;


    @Override
    public int importAll(Long uid) {
        Object user = producerFeign.selectOne(uid);
        log.info("测试远程调用 {}", user);

//        Iterator<ESUser> iterator = esUsers.iterator();
//        int result = 0;
//        while (iterator.hasNext()) {
//            result++;
//            iterator.next();
//        }
//        log.info("{} 条数据已导入ES", result);
//        return result;
        return 0;
    }

    @Override
    public void delete(Long uid) {
        esUserRepository.deleteById(uid);
    }

    @Override
    public ESUser create(Long uid) {
        Optional<ESUser> byId = esUserRepository.findById(uid);
        ESUser save = null;
        if (byId.isPresent()) {
            ESUser esUser = byId.get();
            save = esUserRepository.save(esUser);
        }
        return save;
    }

    @Override
    public void delete(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<ESUser> userList = new ArrayList<>();
            for (Long id : ids) {
                ESUser esUser = new ESUser();
                esUser.setUid(id);
                userList.add(esUser);
            }
            esUserRepository.deleteAll(userList);
        }
    }

    @Override
    public Page<ESUser> search(String nickname, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esUserRepository.findByNickname(nickname, pageable);
    }
}
