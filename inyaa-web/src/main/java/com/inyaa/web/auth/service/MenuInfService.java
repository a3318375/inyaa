package com.inyaa.web.auth.service;

import com.inyaa.web.auth.bean.MenuInfo;
import com.inyaa.web.auth.dao.MenuInfoRepository;
import com.inyaa.web.auth.vo.MenuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/3 23:54
 */
@Service
@RequiredArgsConstructor
public class MenuInfService {

    private final MenuInfoRepository menuInfoRepository;

    private List<MenuInfo> findMenuList(Integer pid) {
        MenuInfo user = new MenuInfo();
        user.setType(0);
        user.setOpen(1);
        user.setPid(pid);
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<MenuInfo> ex = Example.of(user, matcher);
        return menuInfoRepository.findAll(ex);
    }

    private List<MenuVo> findMenuList(List<MenuInfo> list) {
        List<MenuVo> menuVOS = new ArrayList<>();
        list.forEach(menuInfo -> {
            MenuVo vo = new MenuVo();
            vo.setName(menuInfo.getName())
                    .setPath(menuInfo.getPath())
                    .setIcon(menuInfo.getIcon());
            List<MenuInfo> chindres = findMenuList(menuInfo.getId());
            if (chindres.size() > 0) {
                vo.setChildren(findMenuList(chindres));
            }
            menuVOS.add(vo);
        });
        return menuVOS;
    }

    public List<MenuVo> findMenuList() {
        return findMenuList(findMenuList(0));
    }
}
