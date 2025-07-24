package com.basic.app.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.basic.app.dto.requestDto.ComCodeDReqDto;
import com.basic.app.dto.requestDto.ComCodeMReqDto;
import com.basic.app.dto.requestDto.ComCodeTReqDto;
import com.basic.app.service.interfaces.CodeService;

@Service
public class CodeServiceImpl implements CodeService {

  @Override
  public Map<String, Object> findAllGroupCodeForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllGroupCodeForAdmin'");
  }

  @Override
  public Map<String, Object> findByGroupCodeForAdmin(String grpCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByGroupCodeForAdmin'");
  }

  @Override
  public Map<String, Object> insertGroupCodeForAdmin(ComCodeMReqDto comCodeM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertGroupCodeForAdmin'");
  }

  @Override
  public Map<String, Object> updateGroupCodeForAdmin(ComCodeMReqDto comCodeM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateGroupCodeForAdmin'");
  }

  @Override
  public Map<String, Object> deleteGroupCodeForAdmin(String grpCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteGroupCodeForAdmin'");
  }

  @Override
  public Map<String, Object> findAllAttrCodeForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllAttrCodeForAdmin'");
  }

  @Override
  public Map<String, Object> findByAttrCodeForAdmin(String attrCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByAttrCodeForAdmin'");
  }

  @Override
  public Map<String, Object> insertAttrCodeForAdmin(ComCodeTReqDto comCodeT) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertAttrCodeForAdmin'");
  }

  @Override
  public Map<String, Object> updateAttrCodeForAdmin(ComCodeTReqDto comCodeT) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateAttrCodeForAdmin'");
  }

  @Override
  public Map<String, Object> deleteAttrCodeForAdmin(String attrCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteAttrCodeForAdmin'");
  }

  @Override
  public Map<String, Object> findAllDetailCodeForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllDetailCodeForAdmin'");
  }

  @Override
  public Map<String, Object> findByDetailCodeForAdmin(String dtlCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByDetailCodeForAdmin'");
  }

  @Override
  public Map<String, Object> insertDetailCodeForAdmin(ComCodeDReqDto comCodeD) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertDetailCodeForAdmin'");
  }

  @Override
  public Map<String, Object> updateDetailCodeForAdmin(ComCodeDReqDto comCodeD) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateDetailCodeForAdmin'");
  }

  @Override
  public Map<String, Object> deleteDetailCodeForAdmin(String dtlCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteDetailCodeForAdmin'");
  }

}
