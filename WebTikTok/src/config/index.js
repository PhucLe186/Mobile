import Activity from '~/AppComponent/Page/Activity';
import routesconfig from './routes';
import Home from '~/AppComponent/Page/home';
import Explore from '~/AppComponent/Page/explore';
import Following from '~/AppComponent/Page/Following';
import Friend from '~/AppComponent/Page/Friend';
import message from '~/AppComponent/Page/Message';
import Upload from '~/AppComponent/Page/Upload';

const publicRoutes = [
    { path: routesconfig.home, component: Home },
    { path: routesconfig.activity, component: Activity },
    { path: routesconfig.explore, component: Explore },
    { path: routesconfig.following, component: Following },
    { path: routesconfig.friend, component: Friend },
    { path: routesconfig.message, component: message, layout: message },
    { path: routesconfig.Upload, component: Upload },
    // { path: routesconfig.hitry, component: BookingHistory, layout: Onlyheader },
];
export { publicRoutes };
