/**
 * This file is part of CERMINE project.
 * Copyright (c) 2011-2013 ICM-UW
 *
 * CERMINE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CERMINE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with CERMINE. If not, see <http://www.gnu.org/licenses/>.
 */

package pl.edu.icm.cermine.content.model;

/**
 * Document header class.
 *
 * @author Dominika Tkaczyk
 */
public class DocumentHeader {

    private int level;
    private String text;
    private DocumentContentStructure contentStructure;

    public DocumentHeader(int level, String text, DocumentContentStructure contentStructure) {
        this.level = level;
        this.text = text;
        this.contentStructure = contentStructure;
    }

    private DocumentHeader() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DocumentContentStructure getContentStructure() {
        return contentStructure;
    }

    public void setContentStructure(DocumentContentStructure contentStructure) {
        this.contentStructure = contentStructure;
    }

}
