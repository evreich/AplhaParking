import * as React from 'react';

import VKAuthButton from './VKAuthButton';

export default class Registration extends React.Component {
    render() {
        return <div>
            <h2>Страница регистрации</h2>
            <p>Место для формы регистрации</p>
            <hr />
            <VKAuthButton />
        </div>;
    }
}