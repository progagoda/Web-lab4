module.exports = {
            devServer: {
        proxy: {
            "/api/": {
                target: "http://localhost:8085/web_lab4/"
            }
        }
    }
};