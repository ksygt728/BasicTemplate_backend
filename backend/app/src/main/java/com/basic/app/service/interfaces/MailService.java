package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.entity.MailM;

public interface MailService {

  Map<String, Object> findAllMailForAdmin();

  Map<String, Object> findByMailForAdmin(String mailId);

  Map<String, Object> findByMailHistoryForAdmin(String mailId);

  Map<String, Object> insertMailForAdmin(MailM mailM);

  Map<String, Object> updateMailForAdmin(MailM mailM);

  Map<String, Object> deleteMailForAdmin(String mailId);

}
