import Axios from "axios";
import {Message} from "element-ui";

Axios.interceptors.request.use(value => {
  return value;
}, error => {
  Message.error({message: '请求超时'});
  return Promise.resolve(error);
});

Axios.interceptors.response.use(value => {
  if (value && value.status == 200 && value.data.status == 'error') {
    Message.error({message: value.data.msg});
  }
  return value;
}, error => {
  if (error.response.status == 500 || error.response.status == 504) {
    Message.error({message: '服务器挂掉了'});
  } else if (error.response.status == 404) {
    Message.error({message: '页面找不到'});
  } else if (error.response.status == 403) {
    Message.error({message: '没有权限访问'});
  } else {
    Message.error({message: '找到了未知错误'});
  }
  return Promise.resolve(error);
});

export const postRequest = (url, params) => {
  return Axios({
    url: url,
    method: 'post',
    data: params,
    transformRequest: [function (data) {
      let param = '';
      for (let dt in data) {
        param += encodeURIComponent(dt) + '=' + encodeURIComponent(data[dt]) + '&';
      }
      return param;
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
};

export const putRequest = (url, params) => {
  return Axios({
    url: url,
    method: 'put',
    data: params,
    transformRequest: [function (data) {
      let param = '';
      for (let dt in data) {
        param += encodeURIComponent(dt) + '=' + encodeURIComponent(data[dt]) + '&';
      }
      return param;
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
};

export const deleteRequest = (url) => {
  return Axios({
    url: url,
    method: 'delete',
  })
};

export const getRequest = (url) => {
  return Axios({
    url: url,
    method: 'get',
  })
};

export const uploadFileRequest = (url, params) => {
  return Axios({
    url: url,
    method: 'post',
    data: params,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
};
