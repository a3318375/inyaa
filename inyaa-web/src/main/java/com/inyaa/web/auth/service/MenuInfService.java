package com.inyaa.web.auth.service;

import com.inyaa.web.auth.bean.SysMenu;
import com.inyaa.web.auth.dao.SysMenuDao;
import com.inyaa.web.auth.vo.MenuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
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

    private final SysMenuDao sysMenuDao;

    private List<SysMenu> findMenuList(Integer pid) {
        SysMenu user = new SysMenu();
        user.setOpen(1);
        user.setPid(pid);
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<SysMenu> ex = Example.of(user, matcher);
        Sort sort = Sort.by("sort").ascending();
        return sysMenuDao.findAll(ex, sort);
    }

    private List<MenuVo> findMenuList(List<SysMenu> list) {
        List<MenuVo> menuVOS = new ArrayList<>();
        list.forEach(menuInfo -> {
            MenuVo vo = new MenuVo();
            vo.setName(menuInfo.getName())
                    .setPath(menuInfo.getPath())
                    .setCode(menuInfo.getCode())
                    .setIcon(menuInfo.getIcon());
            List<SysMenu> chindres = findMenuList(menuInfo.getId());
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
