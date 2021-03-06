import http from '../public';
import sysConfig from '../sysConfig'
import querystring from "querystring";
const apiUrl=sysConfig.smApiUrlPre;

/**
 * 角色模块
 */
//获取角色列表
export const getRoles=(userId)=>{
  return http.requestGet(apiUrl+'/RoleController/findRole/'+userId)
}
//添加角色
export const addRoles=(data)=>{
  return http.requestPost(apiUrl+'/RoleController/addRole',data)
}
//获取用户角色列表
export const getUserRoles=(rolesId)=>{
  return http.requestGet(apiUrl+'/RoleController/findBasicsRole/'+rolesId)
}
//修改角色
export const editRoles=(data)=>{
  return http.requestPut(apiUrl+'/RoleController/updateRole',data)
}
//删除角色
export const deleteRoles=(rolesId)=>{
  return http.requestDelete(apiUrl+`/RoleController/findBasicsRole/${rolesId}`)
}
//搜索角色
export const searchRoles=(data)=>{
  return http.requestGetFormUr(apiUrl+`/RoleController/findRoleSearch`,data)
}


/**
 * 用户模块
 */
//获取用户列表
export const getUsers=(userId)=>{
  return http.requestGet(apiUrl+'/userController/findUser/'+userId)
}
//添加用户
export const addUser=(data)=>{
  return http.requestPost(apiUrl+'/userController/addUser',data)
}
//修改用户
export const editUser=(data)=>{
  return http.requestPut(apiUrl+'/userController/updateUser',data)
}
//删除用户
export const deleteUser=(userId)=>{
  return http.requestDelete(apiUrl+`/userController/deleteUser/${userId}`)
}
//搜索用户
export const searchUser=(data)=>{
  return http.requestGetFormUr(apiUrl+`/userController/findUserSearch`,data)
}


/**
 * 商品模块
 */
//获取商品数据
export const getProductList=(data)=>{
  return http.requestGetFormUr(apiUrl+`/ProductController/findDetailsList`,data)
}
//获取商品详情
export const getProductCategory=()=>{
  return http.requestGet(apiUrl+`/ProductController/findCategory`)
}
//获取商品属性列表
export const getProductAttributes=()=>{
  return http.requestGet(apiUrl+`/ProductController/findAttributeType`)
}
//添加商品基础信息
export const addProductBasic=(data)=>{
  return http.requestPost(apiUrl+`/ProductController/addProduct`,data)
}
//编辑商品基础信息
export const editProductBasic=(data)=>{
  return http.requestPost(apiUrl+`/ProductController/updateProduct`,data)
}
//修改商品配置库存
export const editProductAttributes=(data)=>{
  return http.requestPut(apiUrl+`/ProductController/updateProductContext`,data)
}
//商品上下架
export const putProduct=(params)=>{
  let queryString = querystring.stringify(params);
  return http.requestPut(apiUrl+`/ProductController/updateProductState?${queryString}`)
}
//商品删除
export const removeProduct=(params)=>{
  let queryString = querystring.stringify(params);
  return http.requestDelete(apiUrl+`/ProductController/deleteProduct?${queryString}`)
}

/**
 * 文件上传模块
 */
//查看文件是否存在
export const getFileVerify=(fileName,chunk,fileSize)=>{
  return http.requestGet(apiUrl+`/filesController/findBreakpointFile/${fileName}/${chunk}/${fileSize}`)
}
//文件上传
export const uploadFiles=(data,uploadProgress)=>{
  return http.requestPostFile(apiUrl+`/filesController/filesUpload`,data,uploadProgress)
}
//文件合成
export const fileMerge=(data)=>{
  return http.requestPostFormUr(apiUrl+`/filesController/compositeFile`,data)
}

/**
 * 订单
 */
export const getOrderList=(data)=>{
  return http.requestGetFormUr(apiUrl+`/OrderController/findAllOrder`,data)
}

/**
 * 首页部分
 */
//获取搜索排行
export const getSearchRanking=(params)=>{
  return http.requestGetFormUr(apiUrl+'/KeyWordController/findKeyWord',params)
}
//获取总销售额
export const getTotalSale=()=>{
  return http.requestGet(apiUrl+'/SalesController/findSales')
}
//商品销量排行
export const getProductRanking=(params)=>{
  return http.requestGetFormUr(apiUrl+'/SalesController/findSalesRanking',params)
}
//获取访问量
export const getViews=(params)=>{
  return http.requestGetFormUr(apiUrl+'/PageViewController/findPageView',params)
}
//获取销售额
export const getSale=(params)=>{
  return http.requestGetFormUr(apiUrl+'/SalesController/findSalesDate',params)
}
//获取支付数
export const getPayRecord=()=>{
  return http.requestGet(apiUrl+'/SalesController/findPayRecord')
}
//获取销售额类占比
export const getSalesCategory=()=>{
  return http.requestGet(apiUrl+'/SalesController/findSalesCategoryTotal')
}