package com.basic.app.service.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.RoleMenuReqDto;
import com.basic.app.dto.requestDto.RoleReqDto;
import com.basic.app.dto.requestDto.RoleUserReqDto;
import com.basic.app.dto.requestDto.UserReqDto;

public interface RoleService {

  Map<String, Object> findAllRoleForAdmin(RoleReqDto roleReqDto, Pageable pageable);

  Map<String, Object> findByRoleForAdmin(String roleCd);

  Map<String, Object> insertRoleForAdmin(RoleReqDto role);

  Map<String, Object> updateRoleForAdmin(RoleReqDto role);

  Map<String, Object> deleteRoleForAdmin(String roleCd);

  Map<String, Object> findByRoleMenuForAdmin(String roleCd);

  Map<String, Object> updateRoleMenuForAdmin(List<RoleMenuReqDto> roleMenuReqDtoList);

  Map<String, Object> findByRoleUserForAdmin(String userId, Pageable pageable);

  Map<String, Object> findAllRoleUserForAdmin(UserReqDto userReqDto, Pageable pageable);

  Map<String, Object> insertRoleUserForAdmin(List<RoleUserReqDto> roleUserReqDtoList);

  Map<String, Object> deleteRoleUserForAdmin(List<RoleUserReqDto> roleUserReqDtoList);

}
