import { getRequest } from '@/api/requests.js'

export default async function (url="", params="", token=undefined) {
	const { data } = await getRequest(url, params, token)
	return data
}