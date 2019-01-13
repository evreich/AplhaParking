import * as React from 'react';
import { Link } from 'react-router-dom';

interface IProps {
    text: string;
}

const LinkToLogin: React.SFC<IProps> = (props) => {
    const { text } = props;
    return <Link to={'/login'}>{text}</Link>;
} ;

export default LinkToLogin;