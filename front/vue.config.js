// module.exports = {
//   devServer: {
//     proxy: "http://localhost:8092/",
//   },
//};

// module.exports = {
//   devServer: {
//     //proxy: "http://i6b206.p.ssafy.io:8092",
//     proxy: "http://localhost:8092/api",
//   },
// };

// module.exports = {
//     devServer: {
//         proxy: {
//             '/api': {
//                 target: 'http://3.38.103.222:8092' // 개발서버
//             }
//         }
//     }
// }

const fs = require('fs');

const SERVER_PATH = '/home/ssl';
module.exports = {
    devServer: {
        https: {
            key: fs.readFileSync(SERVER_PATH + '/together-homt.kro.kr.key'),
            cert: fs.readFileSync(SERVER_PATH + '/together-homt.kro.kr.crt'),
            ca: fs.readFileSync(SERVER_PATH + '/rootca.crt'),
        }
    }
};
