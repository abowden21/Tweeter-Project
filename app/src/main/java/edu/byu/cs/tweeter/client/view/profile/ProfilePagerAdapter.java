package edu.byu.cs.tweeter.client.view.profile;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.tweeter.client.view.main.PlaceholderFragment;
import edu.byu.cs.tweeter.client.view.story.StoryFragment;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.shared.model.domain.AuthToken;
import edu.byu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.client.view.followers.FollowersFragment;
import edu.byu.cs.tweeter.client.view.following.FollowingFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to one of the sections/tabs/pages
 * of the Main Activity.
 */
class ProfilePagerAdapter extends FragmentPagerAdapter {

    private static final int STORY_FRAGMENT_POSITION = 0;
    private static final int FOLLOWING_FRAGMENT_POSITION = 1;
    private static final int FOLLOWERS_FRAGMENT_POSITION = 2;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.storyTabTitle, R.string.followingTabTitle, R.string.followersTabTitle};
    private final Context mContext;
    private final User user;
    private final AuthToken authToken;

    public ProfilePagerAdapter(Context context, FragmentManager fm, User user, AuthToken authToken) {
        super(fm);
        mContext = context;
        this.user = user;
        this.authToken = authToken;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == FOLLOWING_FRAGMENT_POSITION) {
            return FollowingFragment.newInstance(user, authToken);
        } else if (position == FOLLOWERS_FRAGMENT_POSITION) {
            return FollowersFragment.newInstance(user, authToken);
        }
        else if (position == STORY_FRAGMENT_POSITION) {
            return StoryFragment.newInstance(user, authToken);
        }
        else {
            return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}