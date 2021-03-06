package com.iyeeku.ext.rolestaff.service.impl;

import com.iyeeku.core.context.ContextUtil;
import com.iyeeku.core.util.UUIDGenerator;
import com.iyeeku.ext.rolestaff.dao.PFRoleStaffDao;
import com.iyeeku.ext.rolestaff.service.PFRoleStaffService;
import com.iyeeku.ext.rolestaff.vo.PFRoleStaffVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PFRoleStaffServiceImpl implements PFRoleStaffService {

    private final Logger logger = LoggerFactory.getLogger(PFRoleStaffServiceImpl.class);

    @Autowired
    private PFRoleStaffDao pfRoleStaffDao;

    @Override
    public void saveRoleStaff(PFRoleStaffVO roleStaff) {
        roleStaff.setZj(UUIDGenerator.generate(""));
        roleStaff.setCjr(ContextUtil.getLoginUser().getUserId());
        roleStaff.setCjsj(new Date());
        roleStaff.setJlzt("1");
        this.pfRoleStaffDao.insert(roleStaff);
    }

    @Override
    public void deleteRoleStaff(PFRoleStaffVO roleStaffVO) {
        this.pfRoleStaffDao.deleteByYhbhAndJsbh(roleStaffVO.getYhbh() , roleStaffVO.getJsbh());
    }

    @Override
    public boolean hasUnverifidRecord(String yhbh, String jsbh) {
        List<PFRoleStaffVO> list = this.pfRoleStaffDao.findRoleStaffByYhbhAndJsbh(yhbh,jsbh);
        if ( list != null && list.size() > 0 ){
            return true;
        }
        return false;
    }

}
