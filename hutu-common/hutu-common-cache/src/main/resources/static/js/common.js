const basePath = 'http://localhost:8765/cache'
const api = {
    list: {
        method: 'get',
        url: '/cache/list',
    },
    expire: {
        method: 'get',
        url: '/cache/expire',
    },
    delete: {
        method: 'get',
        url: '/cache/delete',
    }
}