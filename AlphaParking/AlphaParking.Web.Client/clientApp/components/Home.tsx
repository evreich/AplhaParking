import * as React from 'react';
import { Link } from 'react-router-dom';
import '../stylesheets/common.scss';

export default class Home extends React.Component {
    render() {
        return <div>
            <h1>Home page</h1>
            <Link to='/login' >Страница входа</Link>
            <Link to='/registration'>Страница регистрации</Link>
        </div>;
    }
}