import * as React from 'react';

import RegistrationForm from './forms/RegistrationForm';
import VKAuthButton from './VKAuthButton';

export default class Registration extends React.Component {
    render() {
        const regBtn = <VKAuthButton text={'Регистрация VK'}/>;
        return <div>
            <h2>Форма регистрации</h2>
            <RegistrationForm regBtn={regBtn} />
        </div>;
    }
}