module.exports={
    devServer:{
        port:9876,
        proxy:{
            '/api':{
                target:'http://localhost:9000',
                changeOrigin:true,
                pathRewrite:{
                    '/api':''
                }

            }
        }
    }
}