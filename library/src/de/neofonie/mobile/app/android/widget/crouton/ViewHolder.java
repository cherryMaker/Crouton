/*
 * Copyright 2012 Neofonie Mobile GmbH
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package de.neofonie.mobile.app.android.widget.crouton;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * ViewHolder <br>
 * <br>
 * <br>
 * The {@link ViewHolder} contains a view that can be used to display a
 * {@link Crouton}.
 */
final class ViewHolder {
	private static final int PADDING = 10;
	private static int defaultTextColor;
	private static RelativeLayout.LayoutParams layoutParams;
	private RelativeLayout view;
	private TextView text;
	private ImageView background;
	private ImageView image;

	private static ViewHolder viewHolder;

	private ViewHolder(Crouton crouton) {
		if (layoutParams == null) {
			layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				crouton.getStyle().height);
		}

		if (defaultTextColor == 0) {
			defaultTextColor = new TextView(
				crouton.getActivity()).getTextColors().getDefaultColor();
		}

		initView(crouton);
	}

	/**
	 * Creates a view for a {@link Crouton}.
	 *
	 * @param crouton The {@link Crouton} that the view should be attached to.
	 * @return The view for this {@link Crouton};
	 */
	public static View viewForCrouton(Crouton crouton) {
		if (viewHolder == null) {
			viewHolder = new ViewHolder(crouton);
		}
		else {
			viewHolder.text.setText(crouton.getText());
		}
		viewHolder.view.setBackgroundColor(
			crouton.getActivity().getResources().getColor(crouton.getStyle().color));

		if (crouton.getStyle().textColor != 0) {
			viewHolder.text.setTextColor(
				crouton.getActivity().getResources().getColor(
					crouton.getStyle().textColor));
		}
		else {
			viewHolder.text.setTextColor(defaultTextColor);
		}

		if (crouton.getStyle().background != 0) {
			Bitmap bm = BitmapFactory.decodeResource(
				crouton.getActivity().getResources(), crouton.getStyle().background);
			BitmapDrawable bd = new BitmapDrawable(
				crouton.getActivity().getResources(), bm);

			if (crouton.getStyle().tile)
				bd.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

			viewHolder.background.setBackgroundDrawable(bd);
		}
		else {
			viewHolder.background.setBackgroundDrawable(null);
		}

		if (crouton.getStyle().image != null) {
			viewHolder.image.setImageDrawable(crouton.getStyle().image);
		}

		return viewHolder.view;
	}

	private void initView(Crouton crouton) {
		view = new RelativeLayout(crouton.getActivity());
		text = new TextView(crouton.getActivity());
		image = new ImageView(crouton.getActivity());
		background = new ImageView(crouton.getActivity());

		view.setLayoutParams(
			new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				crouton.getStyle().height));

		background.setLayoutParams(layoutParams);

		RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
			layoutParams);
		if (crouton.getStyle().height > 0) {
			textParams.setMargins(crouton.getStyle().height, 0, 0, 0);
		}
		text.setLayoutParams(textParams);
		text.setText(crouton.getText());
		text.setTypeface(Typeface.DEFAULT_BOLD);
		text.setPadding(PADDING, PADDING, PADDING, PADDING);
		text.setGravity(crouton.getStyle().gravity);

		image.setPadding(PADDING, PADDING, PADDING, PADDING);
		image.setAdjustViewBounds(true);
		image.setScaleType(ImageView.ScaleType.FIT_XY);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
			crouton.getStyle().height, crouton.getStyle().height);
		lp.addRule(RelativeLayout.LEFT_OF, text.getId());

		view.addView(background);
		view.addView(text);
		view.addView(image, lp);
	}
}
