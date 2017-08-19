package com.puttysoftware.xio;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public final class ZipUtilities {
    private ZipUtilities() {
        // Do nothing
    }

    public static void zipDirectory(final File directory, final File zip)
            throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(
                new FileOutputStream(zip))) {
            zip(directory, directory, zos);
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    private static void zip(final File directory, final File base,
            final ZipOutputStream zos) throws IOException {
        final File[] files = directory.listFiles();
        byte[] buffer = new byte[8192];
        int read = 0;
        int n = files.length;
        for (int i = 0; i < n; i++) {
            if (files[i].isDirectory()) {
                zip(files[i], base, zos);
            } else {
                try (FileInputStream in = new FileInputStream(files[i])) {
                    ZipEntry entry = new ZipEntry(files[i].getPath()
                            .substring(base.getPath().length() + 1));
                    zos.putNextEntry(entry);
                    while (-1 != (read = in.read(buffer))) {
                        zos.write(buffer, 0, read);
                    }
                } catch (IOException ioe) {
                    throw ioe;
                }
            }
        }
    }

    public static void unzipDirectory(final File zip, final File extractTo)
            throws IOException {
        try (ZipFile archive = new ZipFile(zip)) {
            Enumeration<? extends ZipEntry> e = archive.entries();
            while (e.hasMoreElements()) {
                ZipEntry entry = e.nextElement();
                File file = new File(extractTo, entry.getName());
                if (entry.isDirectory() && !file.exists()) {
                    boolean res = file.mkdirs();
                    if (!res) {
                        throw new IOException("Couldn't make folders!");
                    }
                } else {
                    if (!file.getParentFile().exists()) {
                        boolean res = file.getParentFile().mkdirs();
                        if (!res) {
                            throw new IOException("Couldn't make folders!");
                        }
                    }
                    try (InputStream in = archive.getInputStream(entry);
                            BufferedOutputStream out = new BufferedOutputStream(
                                    new FileOutputStream(file))) {
                        byte[] buffer = new byte[8192];
                        int read;
                        while (-1 != (read = in.read(buffer))) {
                            out.write(buffer, 0, read);
                        }
                    } catch (IOException ioe) {
                        throw ioe;
                    }
                }
            }
        } catch (IOException ioe) {
            throw ioe;
        }
    }
}