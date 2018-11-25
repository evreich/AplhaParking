"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var React = require("react");
var ReactDOM = require("react-dom");
var MainLayout_1 = require("./components/common/MainLayout");
var rootElem = document.getElementById('react-root');
ReactDOM.render(React.createElement(MainLayout_1.default, null), rootElem);
if (module.hot)
    module.hot.accept('./components/common/MainLayout', function () {
        var NextApp = require('./components/common/MainLayout').default;
        ReactDOM.render(React.createElement(NextApp, null), rootElem);
    });
//# sourceMappingURL=app.js.map