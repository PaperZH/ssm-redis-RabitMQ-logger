import Axios from './AxiosService'

// 接口地址

//const BASE_PATH = process.env.API_BASEUR  使用了虚拟代理

// 用户登录
export const login = params => { return Axios.post(`/login`, params).then(res => res.data); };

// 获取用户列表
export const getUserList = params => { return Axios.get(`/user/listUsers`, { params: params }).then(res => res.data); };

// 首页加载数据
export const loadData  = params => { return Axios.get(`/home/loadData`).then(res => res.data) };

// 文件上传地址
export const uploadFile = params => { return Axios.post(`/uploadImg`,params).then(res => res.data) };//`/uploadImg`;

//文件下载
export const downloadFile = params => { return Axios.post(`/downloadImg`,params).then(res => res.data) };

//删除用户
export const deleteUser = params => { return Axios.post(`/deleteUser`,params).then(res => res.data) };

//添加用户
export const addUser = params => { return Axios.post(`/addUser`,params).then(res => res.data) };

//更新用户
export const updateUser = params => { return Axios.post(`/updateUser`,params).then(res => res.data) };
