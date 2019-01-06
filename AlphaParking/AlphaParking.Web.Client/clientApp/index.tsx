﻿import * as React from 'react';
import * as ReactDOM from 'react-dom';

import { Provider } from 'react-redux';

import App from './components/App';
import store from './store';
import './stylesheets/common.scss';

declare let module: any;

const rootElem = document.getElementById('react-root');
ReactDOM.render(
    <Provider store={store}>
        <App />
    </Provider>,
    rootElem
);

if (module.hot)
    module.hot.accept('./components/routes/Router', () => {
        const NextApp = require('./components/routes/Router').default;
        ReactDOM.render(<NextApp />, rootElem);
    });