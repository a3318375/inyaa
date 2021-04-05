package com.inyaa.web.auth.service;

import com.inyaa.web.auth.bean.SysMenu;
import com.inyaa.web.auth.dao.SysMenuDao;
import com.inyaa.web.auth.vo.MenuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    private List<SysMenu> findMenuList(Integer pid, int enable) {
        SysMenu user = new SysMenu();
        if (enable == 1) {
            user.setEnable(1);
        }
        user.setPid(pid);
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<SysMenu> ex = Example.of(user, matcher);
        Sort sort = Sort.by("sort").ascending();
        return sysMenuDao.findAll(ex, sort);
    }

    private List<MenuVo> findMenuList(List<SysMenu> list, int enable) {
        List<MenuVo> menuVOS = new ArrayList<>();
        list.forEach(menuInfo -> {
            MenuVo vo = new MenuVo();
            vo.setName(menuInfo.getName())
                    .setPath(menuInfo.getPath())
                    .setPermission(menuInfo.getPermission())
                    .setIcon(menuInfo.getIcon());
            List<SysMenu> chindres = findMenuList(menuInfo.getId(), enable);
            if (chindres.size() > 0) {
                vo.setChildren(findMenuList(chindres, enable));
            }
            menuVOS.add(vo);
        });
        return menuVOS;
    }

    public List<MenuVo> findMenuList(int enable) {
        return findMenuList(findMenuList(0, enable), enable);
    }

    public void delete(SysMenu sysMenu) {
        sysMenuDao.deleteById(sysMenu.getId());
    }

    public void save(SysMenu sysMenu) {
        if (sysMenu.getPid() == null) {
            sysMenu.setPid(0);
        }
        sysMenu.setCreateTime(LocalDateTime.now());
        sysMenuDao.save(sysMenu);
    }
}
