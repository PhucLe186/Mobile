import routesconfig from './routes';
import Home from '~/Page/home';

const publicRoutes = [
    { path: routesconfig.home, component: Home },
    // { path: routesconfig.hitry, component: BookingHistory, layout: Onlyheader },
];
export { publicRoutes };
