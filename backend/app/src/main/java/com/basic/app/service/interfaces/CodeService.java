package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.entity.ComCodeD;
import com.basic.app.entity.ComCodeM;
import com.basic.app.entity.ComCodeT;

public interface CodeService {

  Map<String, Object> findAllGroupCodeForAdmin();

  Map<String, Object> findByGroupCodeForAdmin(String grpCd);

  Map<String, Object> insertGroupCodeForAdmin(ComCodeM comCodeM);

  Map<String, Object> updateGroupCodeForAdmin(ComCodeM comCodeM);

  Map<String, Object> deleteGroupCodeForAdmin(String grpCd);

  Map<String, Object> findAllAttrCodeForAdmin();

  Map<String, Object> findByAttrCodeForAdmin(String attrCd);

  Map<String, Object> insertAttrCodeForAdmin(ComCodeT comCodeT);

  Map<String, Object> updateAttrCodeForAdmin(ComCodeT comCodeT);

  Map<String, Object> deleteAttrCodeForAdmin(String attrCd);

  Map<String, Object> findAllDetailCodeForAdmin();

  Map<String, Object> findByDetailCodeForAdmin(String dtlCd);

  Map<String, Object> insertDetailCodeForAdmin(ComCodeD comCodeD);

  Map<String, Object> updateDetailCodeForAdmin(ComCodeD comCodeD);

  Map<String, Object> deleteDetailCodeForAdmin(String dtlCd);

}
