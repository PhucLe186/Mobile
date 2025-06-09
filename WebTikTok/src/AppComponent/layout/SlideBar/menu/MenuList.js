import { NavLink } from 'react-router-dom';
import classNames from 'classnames/bind';
import styles from './menu.module.scss';

const cx = classNames.bind(styles);

function MenuList({ icon, to, title, click, isActive = null }) {
    const Click = () => {
        if (click) {
            click();
        }
    };

    if (to) {
        return (
            <NavLink
                onClick={Click}
                className={(nav) => cx('list', { active: nav.isActive === true && isActive === null })}
                to={to}
                end
            >
                <span className={cx('icon')}>{icon}</span>
                <span className={cx('text')}>{title}</span>
            </NavLink>
        );
    } else {
        return (
            <button className={cx('list', { active2: isActive !== null })} onClick={Click}>
                <span className={cx('icon')}>{icon}</span>
                <span className={cx('text')}>{title}</span>
            </button>
        );
    }
}

export default MenuList;
