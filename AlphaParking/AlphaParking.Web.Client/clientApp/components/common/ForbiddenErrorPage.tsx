import * as React from 'react';
import { Jumbotron } from 'react-bootstrap';

import LinkToLogin from './LinkToLogin';
import LinkToRegistration from './LinkToRegistration';

const ForbiddenErrorPage: React.SFC = () => {
    return <Jumbotron>
        <h1>Уважаемый пользователь!</h1>
        <h2>Возникла ошибка доступа к контенту по данному пути.</h2>
        <h2>Необходимо зайти под учетной записью с привелегиями "служащего" компании</h2>
        <h2>Для того, чтобы увидеть предлагаемый контент, выполните <LinkToLogin text={'вход в систему'} /> или <LinkToRegistration text={'зарегистрируйтесь'} /> </h2>
    </Jumbotron>;
};

export default ForbiddenErrorPage;