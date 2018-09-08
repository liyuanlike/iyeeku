package com.iyeeku.ext.function.web;

import com.iyeeku.ext.function.service.PFResMenuService;
import com.iyeeku.ext.function.vo.PFResMenuVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/menu")
public class PFResMenuController {

    private final Logger logger = LoggerFactory.getLogger(PFResMenuController.class);

    @Autowired
    private PFResMenuService pfResMenuService;

    @RequestMapping(value = "/list" , method = RequestMethod.GET , name = "菜单配置主页面")
    public ModelAndView list(){
        return new ModelAndView("ext/menu/menuMain");
    }


    @RequestMapping( value = "/findAllMenu" , method = RequestMethod.POST , name = "查询所有菜单")
    @ResponseBody
    public Map<String , Object> findAllMenu(){
        this.logger.info("PFMenuController findAllMenus");

        //TODO 注意这里，开启 缓存后，有BUG
        List<PFResMenuVO> list = this.pfResMenuService.findAllMenu();

        PFResMenuVO menuVO = new PFResMenuVO();
        menuVO.setCdbh("DEFAULT_MENUTREE_ROOT");
        menuVO.setCdbm("1");
        menuVO.setCdmc("菜单树");
        list.add(menuVO);

        List<Map<String,String>> retList = new ArrayList<>();
        Map<String,String> retMap = null;
        for (PFResMenuVO resMenuVO : list){
            retMap = new HashMap<>();
            retMap.put("id",resMenuVO.getCdbh());
            retMap.put("text",resMenuVO.getCdmc());
            retMap.put("pid",resMenuVO.getSjcdbh());
            //retMap.put("cdurlbh",resMenuVO.getCdurlbh());
            retMap.put("cdurl",resMenuVO.getCdurl());
            retList.add(retMap);
        }

        Map<String,Object> menuMap = new HashMap<>();
        menuMap.put("treeNodes",retList);
        return menuMap;
    }

    @RequestMapping(value = "/add" , method = RequestMethod.POST , name = "新增菜单")
    @ResponseBody
    public void add(PFResMenuVO resMenuVO){

    }

    @RequestMapping(value = "/update" , method = RequestMethod.POST , name = "更新菜单信息")
    @ResponseBody
    public void update(PFResMenuVO resMenuVO){

    }


    @RequestMapping(value = "/getMenuInfoByCdbh" , method = RequestMethod.POST , name = "根据菜单编号查询菜单信息")
    @ResponseBody
    public PFResMenuVO getMenuInfoByCdbh(String cdbh){
        return this.pfResMenuService.findMenuByCdbh(cdbh);
    }

    @RequestMapping(value = "/navMenu" , name = "查询导航菜单")
    @ResponseBody
    public List<Map<String,Object>> navMenu(){
        List<PFResMenuVO> list = this.pfResMenuService.findAll("DEFAULT_MENUTREE_ROOT");
        return walkMenuTreeData(list);
    }

    private List<Map<String,Object>> walkMenuTreeData(List<PFResMenuVO> list){
        List<Map<String,Object>> rtnList = new ArrayList<>();
        Map<String ,Object> rtnMap = null;
        for (PFResMenuVO resMenuVO : list){
            rtnMap = new HashMap<>();
            if(resMenuVO.getChildren() == null || resMenuVO.getChildren().size() == 0){
                rtnMap.put("id",resMenuVO.getCdbh());
                rtnMap.put("iconCls",resMenuVO.getCdtp());
                rtnMap.put("text" , resMenuVO.getCdmc());
                rtnMap.put("url", resMenuVO.getCdurl());
                rtnList.add(rtnMap);
            }else{
                rtnMap.put("id",resMenuVO.getCdbh());
                rtnMap.put("iconCls",resMenuVO.getCdtp());
                rtnMap.put("text" , resMenuVO.getCdmc());
                rtnMap.put("children",walkMenuTreeData(resMenuVO.getChildren()));
                rtnList.add(rtnMap);
            }
        }
        return rtnList;
    }



}