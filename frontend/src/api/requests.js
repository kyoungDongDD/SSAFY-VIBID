import axios from "axios";
const BASE_URL = "https://i6b207.p.ssafy.io:8080/";

const axiosService = axios.create({
  baseURL: BASE_URL,
});

export function postRequest(url, data, token = undefined) {
  if (token === undefined) return axiosService.post(url, data);
  else
    return axiosService.post(url, data, {
      headers: { Authorization: `Bearer ${token}` },
    });
}

export function putRequest(url, data, token=undefined) {
  if(token === undefined) return axiosService.put(url, data)
	else return axiosService.put(url, data, { headers: { "Authorization" : `Bearer ${token}`}})
}

export function patchRequest(url, data, token=undefined) {
  if(token === undefined) return axiosService.patch(url, data)
	else return axiosService.patch(url, data, { headers: { "Authorization" : `Bearer ${token}`}})}

export function deleteRequest (url, data) {
	return axiosService.delete(url, data)
}

export function getRequest (url, params = '', token= undefined) {
	if(params){
		const keys = Object.keys(params)
		const values = Object.values(params)
		params = '?' + keys.map((key, i) => values[i] === "" ? "" : `${key}=${values[i]}&`).join('').slice(0,-1)
	}
	if(token === undefined) return axiosService.get(url+params)
	else return axiosService.get(url+params, { headers: { Authorization : `Bearer ${token}`}})
}
