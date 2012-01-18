/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.server.util;

import hudson.gwtmarketplace.domain.manager.AbstractManager;
import net.sf.jsr107cache.Cache;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class ImageUtil {

	public static byte[] iconize (String key) {
		Cache cache = AbstractManager.getCache();
		String cacheKey = "thumbs:" + key;
		byte[] data = (byte[]) cache.get(cacheKey);
		if (null != data) {
			return data;
		}
		else {
			BlobKey blobKey = new BlobKey(key);
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
	        Image oldImage = ImagesServiceFactory.makeImageFromBlob(blobKey);
	        Transform resize = ImagesServiceFactory.makeResize(160, 600);
	        Image newImage = imagesService.applyTransform(resize, oldImage);
	        if (null != newImage) {
	        	data = newImage.getImageData();
	        	cache.put(cacheKey, data);
	        }
		}
		return data;
	}
}
