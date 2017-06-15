package com.umasuo.file.infrastructure;

/**
 * Created by umasuo on 17/6/15.
 */
public class Router {

  public static final String FILE_ROOT = "/files";

  public static final String FILE_WITH_ID = FILE_ROOT + "/{id}";

  public static final String FILE_ON_LOCAL = FILE_ROOT + "/p/";
  public static final String FILE_ON_LOCAL_WITH_ID = FILE_ROOT + "/p/{id:.+}";

}
